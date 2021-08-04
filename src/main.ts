import express from "express";
import { getDynamoDb } from "./utils";
import errorhandler from "errorhandler";
import { organizationDao, userDao } from "./dao";
import Keycloak from "keycloak-connect";
import session from "express-session";
// import { is } from "typescript-is";
import { CreateUser } from "./models";

process.on("uncaughtException", (err) => {
  console.log("ERROR", err);
});

const memoryStore = new session.MemoryStore();
const keycloak = new Keycloak({
  store: memoryStore,
});

const { docClient } = getDynamoDb();
const userDaoImpl = userDao(docClient);
const orgDaoImpl = organizationDao(docClient);

const app = express();

app.use(
  session({
    resave: false,
    saveUninitialized: true,
    secret: "ijsd07sdfhsd98hfyu2b3r9dsfasd", // TODO: move to file
    store: memoryStore,
  })
);

app.use(keycloak.middleware());
app.use(errorhandler());
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.post("/user", async (req, res) => {
  // TODO: Get typescript-is to work
  // if (is<CreateUser>(req.body)) {
  //   res.sendStatus(500);
  // }

  try {
    const newUser = await userDaoImpl.create(req.body);
    res.send(newUser);
  } catch (error) {
    console.error(error);

    if (error.message === "organization-not-found") {
      res.sendStatus(404);
    }

    res.sendStatus(500);
  }
});

app.get("/test", async (req, res) => {
  // const u = await userDaoImpl.getByLoginDetails('tim@example.com', '123456');
  // res.send(u);

  const org = await orgDaoImpl.create({
    name: "Baptist Church",
    country: "USA",
    state: "CA",
    city: "Bakersfield",
    phoneNumber: "800-555-5555",
  });
  res.send(org);
});

app.get("/organizations/:id", keycloak.protect(), async (req, res) => {
  const org = await orgDaoImpl.getById(req.params.id);
  if (!org) return res.sendStatus(404);
  res.send(org);
});

app.get("/auth/login", keycloak.redirectToLogin);

app.listen(8080, () => {
  console.log("Listening on http://localhost:8080/");
});

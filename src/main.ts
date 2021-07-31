import express from 'express';
import {getDynamoDb} from "./utils";
import errorhandler from 'errorhandler';
import bodyParser from 'body-parser';
import {userDao} from "./dao";

process.on('uncaughtException', (err) => {
  console.log('ERROR', err);
});

const { docClient } = getDynamoDb();
const userDaoImpl = userDao(docClient);

const app = express();
app.use(errorhandler());
app.use(bodyParser.json({ type: 'application/*+json' }));

app.get('/test', async (req, res) => {
  const u = await userDaoImpl.getByLoginDetails('tim@example.com', '123456');
  res.send(u);
});

app.post('/auth/login', async (req, res) => {
  // TODO: stronger typing on body.
  const user = userDaoImpl.getByLoginDetails(req.body.email, req.body.password);
  if (user === undefined) {
    return res.status(401).send('Unauthorized');
  }
  // TODO: set/return auth
});


app.listen(8080, () => {
  console.log('Listening on http://localhost:8080/');
});
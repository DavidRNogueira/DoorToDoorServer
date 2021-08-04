import { DynamoDBDocument } from "@aws-sdk/lib-dynamodb";
import { CreateUser, User } from "../models";
import { v4 as uuidv4 } from "uuid";
import { organizationDao } from "./organization";

export const TableName = "users";

export const IndexByEmail = `${TableName}-Email`;

export const userDao = (client: DynamoDBDocument) => {
  const orgDaoImpl = organizationDao(client);

  const getByEmail = async (email: string): Promise<User | undefined> => {
    const obj = await client.query({
      TableName,
      IndexName: IndexByEmail,
      KeyConditionExpression: "email = :email",
      ExpressionAttributeValues: {
        ":email": email,
      },
      Limit: 1,
    });
    if (obj.Items?.length) return obj.Items[0] as User;
  };

  const create = async (
    newUser: CreateUser
  ): Promise<User | Error | undefined> => {
    const organization = await orgDaoImpl.getById(newUser.organization);

    if (!organization) {
      throw new Error("organization-not-found");
    }

    try {
      // TODO: validation
      const id = uuidv4();
      await client.put({
        TableName,
        Item: {
          id,
          firstName: newUser.firstName,
          lastName: newUser.lastName,
          email: newUser.email,
          phoneNumber: newUser.phoneNumber,
          addressOne: newUser.addressOne,
          addressTwo: newUser.addressTwo,
          city: newUser.city,
          state: newUser.state,
          country: newUser.country,
          zipCode: newUser.zipCode,
          role: newUser.role,
          organization: newUser.organization,
        },
      });

      return getByEmail(newUser.email);
    } catch (error) {
      throw new Error(error);
    }
  };

  return {
    getByEmail,
    create,
  };
};

import { DynamoDBDocument } from "@aws-sdk/lib-dynamodb";
import { User } from "../models";

export const TableName = 'users';

export const IndexByEmail = `${TableName}-Email`;

export const userDao = (client: DynamoDBDocument) => {
  const getByEmail = async (email: string): Promise<User | undefined> => {
    const obj = await client.query({
      TableName,
      IndexName: IndexByEmail,
      KeyConditionExpression: 'email = :email',
      ExpressionAttributeValues: {
        ':email': email,
      },
      Limit: 1,
    });
    if (obj.Items?.length) return obj.Items[0] as User;
  };

  return {
    getByEmail,
  };
};

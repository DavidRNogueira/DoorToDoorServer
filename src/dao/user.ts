import { DynamoDBDocument } from "@aws-sdk/lib-dynamodb";
import { User } from "../models";
import bcrypt from 'bcrypt';

export const TableName = 'users';

export const IndexByEmail = `${TableName}-Email`;

type UserInternal = Omit<User, 'password'> & Required<Pick<User, 'password'>>;

const sanitizeUser = (user?: UserInternal): User | undefined => {
  if (!user) return undefined;
  const newUser: User = { ...user };
  delete newUser.password;
  return newUser;
}

export const userDao = (client: DynamoDBDocument) => {
  const getByEmailInternal = async (email: string): Promise<UserInternal | undefined> => {
    const obj = await client.query({
      TableName,
      IndexName: IndexByEmail,
      KeyConditionExpression: 'email = :email',
      ExpressionAttributeValues: {
        ':email': email,
      },
      Limit: 1,
    });
    if (obj.Items?.length) return obj.Items[0] as UserInternal;
  };

  const getByEmail = async (email: string): Promise<User | undefined> => {
    return sanitizeUser(await getByEmailInternal(email));
  };

  const getByLoginDetails = async (email: string, password: string): Promise<User | undefined> => {
    const user = await getByEmailInternal(email);
    if (user === undefined) return undefined;

    const valid = await bcrypt.compare(password, user.password);
    if (valid) return sanitizeUser(user);
  };

  return {
    getByEmail,
    getByLoginDetails,
  };
};

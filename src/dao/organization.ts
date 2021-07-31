import {DynamoDBDocument} from "@aws-sdk/lib-dynamodb";
import {Organization,} from "../models";

export const TableName = 'organizations';

export const organizationDao =  (client: DynamoDBDocument) => {
  const getById = async (id: string): Promise<Organization | undefined> => {
    const obj = await client.get({
      TableName,
      Key: {
        id,
      },
    });
    if (obj.Item) return obj.Item as Organization;
  };

  return {
    getById
  };
};

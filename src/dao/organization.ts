import { DynamoDBDocument } from "@aws-sdk/lib-dynamodb";
import { Organization } from "../models";
import { v4 as uuidv4 } from "uuid";

export const TableName = "organizations";

export type CreateOrganization = Pick<
  Organization,
  "name" | "city" | "state" | "country" | "phoneNumber"
>;

export const organizationDao = (client: DynamoDBDocument) => {
  const getById = async (id: string): Promise<Organization | undefined> => {
    const obj = await client.get({
      TableName,
      Key: {
        id,
      },
    });
    if (obj.Item) return obj.Item as Organization;
  };

  const create = async (
    create: CreateOrganization
  ): Promise<Organization | undefined> => {
    // TODO: validation
    const id = uuidv4();
    await client.put({
      TableName,
      Item: {
        id,
        name: create.name,
        city: create.city,
        state: create.state,
        country: create.country,
        phoneNumber: create.phoneNumber,
        salvations: 0,
      },
    });
    return getById(id);
  };

  return {
    getById,
    create,
  };
};

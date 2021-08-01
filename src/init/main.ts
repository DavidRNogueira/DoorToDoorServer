import {getDynamoDb} from "../utils";
import * as user from '../dao/user';
import * as organization from '../dao/organization';

const { dynamoDB, docClient } = getDynamoDb();

(async () => {
  // Delete existing tables
  const tables = await dynamoDB.listTables({});
  await Promise.all(tables.TableNames?.map(TableName => dynamoDB.deleteTable({TableName})) ?? []);

  // Organizations table
  await dynamoDB.createTable({
    TableName: organization.TableName,
    AttributeDefinitions: [
      {AttributeName: 'id', AttributeType: 'S'},
    ],
    KeySchema: [
      {AttributeName: 'id', KeyType: 'HASH'},
    ],
    BillingMode: 'PAY_PER_REQUEST'
  });

  // Users table
  await dynamoDB.createTable({
    TableName: user.TableName,
    AttributeDefinitions: [
      {AttributeName: 'id', AttributeType: 'S'},
      {AttributeName: 'email', AttributeType: 'S'},
    ],
    KeySchema: [
      {AttributeName: 'id', KeyType: 'HASH'},
    ],
    GlobalSecondaryIndexes: [
      {
        IndexName: user.IndexByEmail,
        KeySchema: [
          {AttributeName: 'email', KeyType: 'HASH'},
        ],
        Projection: {
          ProjectionType: 'ALL'
        },
      },
    ],
    BillingMode: 'PAY_PER_REQUEST'
  });
})();

import {getDynamoDb} from "../utils";
import * as user from '../dao/user';
import bcrypt from 'bcrypt';

const { dynamoDB, docClient } = getDynamoDb();

(async () => {
  // Delete existing tables
  const tables = await dynamoDB.listTables({});
  await Promise.all(tables.TableNames?.map(TableName => dynamoDB.deleteTable({TableName})) ?? []);

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

  // Test data
  await docClient.put({
    TableName: user.TableName,
    Item: {
      id: '123124235',
      email: 'tim@example.com',
      firstName: 'Tim',
      lastName: 'Cooper',
      password: await bcrypt.hash('123456', 10),
    },
  })
})();

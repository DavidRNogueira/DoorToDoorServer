import {DynamoDB } from "@aws-sdk/client-dynamodb";
import {DynamoDBDocument} from "@aws-sdk/lib-dynamodb";

export const getDynamoDb = () => {
  const dynamoDB = new DynamoDB({
    region: 'us-west-2',
    endpoint: "http://localhost:8000/",
    credentials: {
      accessKeyId: 'fakeMyKeyId',
      secretAccessKey: 'fakeSecretAccessKey'
    }
  });
  const docClient = DynamoDBDocument.from(dynamoDB);
  return { dynamoDB, docClient };
};

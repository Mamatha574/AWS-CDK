package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.EnableScalingProps;
import software.amazon.awscdk.services.dynamodb.IScalableTableAttribute;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.dynamodb.TableProps;
import software.amazon.awscdk.services.iam.IRole;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.kinesis.Stream;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.IBucket;

public class CdkWorkshopStack extends Stack {
	public CdkWorkshopStack(final Construct parent, final String id) {
		this(parent, id, null);
	}

	public CdkWorkshopStack(final Construct parent, final String id, final StackProps props) {
		super(parent, id, props);

		// Bucket bucket = new Bucket(this, "FSMBucket9");
		IBucket bucketName = Bucket.fromBucketName(this, "BucketByName", "my-fsm-bucket");

		IRole role = Role.fromRoleArn(this, "role", "arn:aws:iam::214303766158:role/role");

		final Function hello = Function.Builder.create(this, "lambda_handler").runtime(Runtime.PYTHON_3_6) // execution
																											// environment
				.code(Code.fromBucket(bucketName, "lambda_function.zip")) // code loaded from the "lambda" directory
				.handler("lambda_function.lambda_handler").build();

		// created dynamodb table
		Table dynamotable = new Table(this, "Fsm_table75", TableProps.builder()
				.partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build()).build());

		IScalableTableAttribute readScaling = dynamotable
				.autoScaleReadCapacity(new EnableScalingProps.Builder().minCapacity(1).maxCapacity(1000).build());
		IScalableTableAttribute writeScaling = dynamotable
				.autoScaleWriteCapacity(new EnableScalingProps.Builder().minCapacity(1).maxCapacity(1000).build());

		// create kinesis

		Stream source_stream = Stream.Builder.create(this, "MySourceStream").streamName("fsm-source-stream")
				.shardCount(3).retentionPeriod(Duration.hours(48)).build();

		Stream destination_stream = Stream.Builder.create(this, "MyDestinationStream")
				.streamName("fsm-destination-stream").shardCount(1).retentionPeriod(Duration.hours(24)).build();
//Map<Object,Object> input1 = new HashMap<>();
//  input1.put("name", "rpm");
//  input1.put("sqlType", "integer");
//  input1.put("mapping", "$.rpm");
//  
//  
//  Map<Object,Object> input2 = new HashMap<>();
//  input2.put("name","COL_timestamp");
//  input2.put("sqlType","BIGINT");
//  input2.put("mapping","$.timestamp");
//  
//  List<Object> response = new ArrayList<>();
//  response.add(input1);
//  response.add(input2);

//BucketDeployment.Builder.create(this, "DeployWebsite")
//.sources(asList(s3deploy.Source.asset("./website-dist")))
//.destinationBucket(bucket)
//.build();

//CfnApplication app   =  CfnApplication.Builder.create(this,"kinesis")
//
//.applicationCode("CREATE OR REPLACE STREAM \"DESTINATION_SQL_STREAM\" \n"
//+ "(\n"
//+ "\"rpm\" integer, \n"
//+ "\"COL_timestamp\" bigint\n"
//+ ");\n"
//+ "\n"
//+ " \n"
//+ "\n"
//+ "CREATE OR REPLACE PUMP \"STREAM_PUMP\" AS INSERT INTO \"DESTINATION_SQL_STREAM\"\n"
//+ "\n"
//+ " \n"
//+ "\n"
//+ "SELECT stream \"rpm\",\"COL_timestamp\"\n"
//+ "     FROM \"SOURCE_SQL_STREAM_001\"\n"
//+ "   group by \"rpm\",\"COL_timestamp\",\n"
//+ "STEP(\"SOURCE_SQL_STREAM_001\".ROWTIME BY INTERVAL '10' SECOND);").applicationName("stream").inputs((IResolvable) InputProperty.builder().namePrefix("kinesis_prefix").inputSchema(InputSchemaProperty.builder()
//      .recordColumns((IResolvable) RecordColumnProperty.builder().name("rpm").sqlType("integer").mapping("$.rpm").name("COL_timestamp")
//       .sqlType("BIGINT")
//   .mapping("$.timestamp").build()).build()).build()).build();
//
//CfnApplication.KinesisStreamsInputProperty.builder().roleArn(role.getRoleArn()).resourceArn(source_stream.getStreamName()).build();
//
//CfnApplicationOutput.DestinationSchemaProperty.builder().recordFormatType("JSON").build();
//CfnApplicationOutput.KinesisStreamsOutputProperty.builder().roleArn(role.getRoleArn()).resourceArn(destination_stream.getStreamName());

//
//KinesisActionProperty    action1=CfnTopicRule.KinesisActionProperty.builder()
//.roleArn(role.getRoleArn()).partitionKey("${topic()}").streamName(source_stream.getStreamName()).build();
//DynamoDBActionProperty        action2=CfnTopicRule.DynamoDBActionProperty.builder().tableName("RuleTable").roleArn(role.getRoleArn())
//.hashKeyField("timestamp").hashKeyType("STRING").hashKeyValue("${timestamp()}")
//.rangeKeyField("timestamp2").rangeKeyType("STRING").rangeKeyValue("${timestamp()}").build();
//System.out.println("kinesis and dynamic" + action1 + action2); 
////
//List<Object> action = new ArrayList<Object>();
//action.add(action1);
//action.add(action2);
//CfnTopicRuleProps tableProps = CfnTopicRuleProps.builder()
//.ruleName("fsm_iot_rule").topicRulePayload(TopicRulePayloadProperty.builder().sql("SELECT * FROM 'topic/test'").actions(action).ruleDisabled(false).build()).build();
//
//CfnTopicRule iot_rule=new CfnTopicRule(this,"fsm_iot_rule",tableProps);
//
//TopicRulePayloadProperty topicRulePayload=TopicRulePayloadProperty.builder().sql("SELECT * FROM 'topic/test'").actions(action).build();
//CfnTopicRule.TopicRulePayloadProperty(
//CfnTopicRuleProps.TopicRulePayloadProperty.builder().sql("").awsIotSqlVersion("").description("")));
//
//CfnTopicRule topicRule=  CfnTopicRule.Builder.create(this, "lambda_handler")

	}
}

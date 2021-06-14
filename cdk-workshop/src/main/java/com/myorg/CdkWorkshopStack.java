package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;


import software.amazon.awscdk.services.kinesis.Stream;

import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.dynamodb.TableProps;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.lambda.*;
//import path.*;


public class CdkWorkshopStack extends Stack {
    public CdkWorkshopStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public CdkWorkshopStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        
          
        final Function hello = Function.Builder.create(this, "HelloHandler")
            .runtime(Runtime.NODEJS_10_X)    // execution environment
            .code(Code.fromAsset("lambda"))  // code loaded from the "lambda" directory
            .handler("hello.handler")        // file is "hello", function is "handler"
            .build();  

           //created dynamodb table
         Table table = new Table(this, "Table", TableProps.builder()
                .partitionKey(Attribute.builder()
                        .name("id")
                        .type(AttributeType.STRING)
                        .build())
                .build());

                //create kinesis

      Stream.Builder.create(this, "MyFirstStream")
         .streamName("my-awesome-stream")
         .shardCount(3)
         .retentionPeriod(Duration.hours(48))
         .build();

// created lambda function
     /*    Function fn = new Function(this, "MyFunction", new FunctionProps()
         .runtime(lambda.Runtime.getNODEJS_10_X())
         .handler("index.handler")
         .code(lambda.Code.fromAsset(path.join(__dirname, "lambda-handler"))));
                
*/

 Table table = new Table(this, "Fsm_table", TableProps.builder()
                .partitionKey(Attribute.builder()
                        .name("id")
                        .type(dynamodb.AttributeType.STRING)
                        .build())
                .build());
      // table = dynamodb.Table('Fsm_table')
    /*fe = Attr('timestamp').eq("timestamp2")
            #item count in table
       #print('count',table.item_count)  */
      float start = time.time();
       float[] result_item;
       float[]  final_result;
       float[] onlytime;
       float[] final;
       float[] test;
       result_data = table.scan()
       result_item.extend(result_data['Items'])

       sorted_result = sorted(result_item, key = lambda i: i['timestamp'])

       Hello = sorted_result
       for( float x : sorted_result){
          x['timestamp'] = int(x['timestamp'])
          onlytime.append(x['timestamp'])
       }


       max_time = max(onlytime)
       min5 = max_time-5000
       for (float x : sorted_result){
           if(x['timestamp'] > min5 && x['timestamp'] < max_time){
               final_result.append(x);}
       test = final_result;
       final = [Hello,test]
      // print(type(final),type(sorted_result),type(Hello),type(final_result))
       return final
       }
    }
}

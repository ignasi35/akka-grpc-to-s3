package com.example.helloworld;

import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.grpc.javadsl.Metadata;
import akka.stream.alpakka.s3.MultipartUploadResult;
import akka.stream.alpakka.s3.javadsl.S3;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;

import java.util.concurrent.CompletionStage;

class GreeterServiceImpl implements GreeterServicePowerApi {

    final ActorSystem<?> system;


    public GreeterServiceImpl(ActorSystem<?> system) {
        this.system = system;
    }

    @Override
    public CompletionStage<MyResponse> multiUpload(Source<MyRequest, NotUsed> in, Metadata metadata) {
        System.out.println("Processing inbound stream " + in.hashCode() + "...");

        String key = metadata
                .getText("S3-key")
                .orElseThrow(() -> new RuntimeException("Missing header 'S3-key'."));
        Sink<ByteString, CompletionStage<MultipartUploadResult>> s3MultipartSink =
                S3.multipartUpload(
                        "the-bucket-name",
                        key);

        Source<ByteString, NotUsed> chunkSource =
                in.map(request -> request.getPayload().toByteArray())
                        .map(ByteString::fromArray);
        CompletionStage<MultipartUploadResult> multipartUploadResultCompletionStage = chunkSource.runWith(s3MultipartSink, system);
        return multipartUploadResultCompletionStage
                .thenApply(x -> MyResponse.newBuilder().setMessage("done").build());
    }


}



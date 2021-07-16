package dev.etimbuk.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BlobInput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.StorageAccount;

import java.util.Optional;

public class FileDownloadHttpTriggerFunction {

    @FunctionName("download")
    @StorageAccount("STORAGE-ACCOUNT-NAME-FROM-THIS-DEPLOYED-FUNCTION")
    public HttpResponseMessage downloadFile(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            @BlobInput(
                    name = "file",
                    dataType = "binary",
                    path = "java-functions-container/azurefxicon.png",
                    connection = "AzureWebJobsStorage")
                    byte[] content,
            final ExecutionContext context) {
        context.getLogger().info("FileDownloadHttpTrigger processed a request.");

        if (content != null  && content.length > 0) {
            return request.createResponseBuilder(HttpStatus.OK)
                    .body(content)
                    .build();
        } else {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("The searched file does not exist.")
                    .build();
        }
    }
}

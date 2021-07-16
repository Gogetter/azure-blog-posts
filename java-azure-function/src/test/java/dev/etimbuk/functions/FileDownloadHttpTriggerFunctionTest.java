package dev.etimbuk.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileDownloadHttpTriggerFunctionTest {
    @Mock
    private HttpRequestMessage<Optional<String>> httpRequestMessage;

    @Mock
    private ExecutionContext context;

    @Test
    public void testHttpTriggerJava() {
        final byte[] content = "Testing".getBytes(StandardCharsets.UTF_8);

        when(httpRequestMessage.createResponseBuilder(any(HttpStatus.class)))
                .thenReturn(new HttpResponseMessageMock.HttpResponseMessageBuilderMock()
                        .body(content)
                        .status(HttpStatus.OK));

        when(context.getLogger()).thenReturn(Logger.getGlobal());

        // Invoke
        final HttpResponseMessage ret = new FileDownloadHttpTriggerFunction().downloadFile(httpRequestMessage,
                content, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }
}

package dio.budgeting.infrastructure.http;

import dio.budgeting.application.GetFinancialSummaryUseCase;
import dio.budgeting.application.ListTransactionsByCategoryUseCase;
import dio.budgeting.application.PersistTransactionUseCase;
import dio.budgeting.application.output.FinancialSummaryOutput;
import org.junit.jupiter.api.Test;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class TransactionControllerTest {

    private final PersistTransactionUseCase persistTransactionUseCase =
            mock(PersistTransactionUseCase.class);

    private final ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase =
            mock(ListTransactionsByCategoryUseCase.class);

    private final GetFinancialSummaryUseCase getFinancialSummaryUseCase =
            mock(GetFinancialSummaryUseCase.class);

    private final TranscriptionModel transcriptionModel =
            mock(TranscriptionModel.class);

    private final Resource systemPrompt =
            mock(Resource.class);

    private final ChatClient.Builder chatClientBuilder =
            mock(ChatClient.Builder.class);

    private final TextToSpeechModel textToSpeechModel =
            mock(TextToSpeechModel.class);

    private final MockMvc mockMvc;

    TransactionControllerTest() throws Exception {

        when(systemPrompt.getContentAsString(any(Charset.class)))
                .thenReturn("Você é um assistente financeiro.");

        when(chatClientBuilder.defaultSystem(any(String.class)))
                .thenReturn(chatClientBuilder);

        when(chatClientBuilder.defaultTools(any(Object[].class)))
                .thenReturn(chatClientBuilder);

        when(chatClientBuilder.build())
                .thenReturn(mock(ChatClient.class));

        var controller = new TransactionController(
                persistTransactionUseCase,
                listTransactionsByCategoryUseCase,
                getFinancialSummaryUseCase,
                transcriptionModel,
                systemPrompt,
                chatClientBuilder,
                textToSpeechModel
        );

        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnFinancialSummary() throws Exception {

        when(getFinancialSummaryUseCase.execute())
                .thenReturn(new FinancialSummaryOutput(
                        4,
                        260.0,
                        65.0,
                        100.0,
                        30.0
                ));

        mockMvc.perform(get("/transactions/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTransactions").value(4))
                .andExpect(jsonPath("$.totalAmount").value(260.0))
                .andExpect(jsonPath("$.averageAmount").value(65.0))
                .andExpect(jsonPath("$.highestAmount").value(100.0))
                .andExpect(jsonPath("$.lowestAmount").value(30.0));
    }
}

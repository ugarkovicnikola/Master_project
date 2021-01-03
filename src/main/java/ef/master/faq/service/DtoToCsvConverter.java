package ef.master.faq.service;

import ef.master.faq.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DtoToCsvConverter {

  public byte[] convert(List<PostResponse> postList) throws IOException {

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

    ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
    String[] csvHeader = {"Post ID", "Title", "Content", "Number of comments"};
    String[] csvMapping = {"id", "title", "content", "numberOfComments"};

    csvBeanWriter.writeHeader(csvHeader);

    for (PostResponse postResponse : postList) {
      csvBeanWriter.write(postResponse, csvMapping);
    }
    csvBeanWriter.close();

    return outputStream.toByteArray();
  }
}

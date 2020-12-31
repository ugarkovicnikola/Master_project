package ef.master.faq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional
public class CronejobExport {

  private final PostService postService;

  @Value("${ef.master.faq.exportFilePath}")
  private String path;

  @Value("${ef.master.faq.fileExstension}")
  private String extension;

  @Scheduled(cron = "${ef.master.faq.cronejobFrequency}")
  public void cronejobExport() throws IOException {
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());

    byte[] postList = postService.exportAsCSV();
    String fileName = path.concat(currentDateTime.concat(extension));
    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
    fileOutputStream.write(postList);
  }
}

package com.nro.demo.webclientanalyze;

import com.nro.demo.webclientanalyze.client.DetectLabelResponse;
import com.nro.demo.webclientanalyze.client.FileNameRequest;
import com.nro.demo.webclientanalyze.client.SignUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class WebclientAnalyzeApplication implements CommandLineRunner {

    @Value("${app.download-url}")
    private String urlDownload;

    @Value("${app.api-key}")
    private String apikey;

    public static void main(String[] args) {
        SpringApplication.run(WebclientAnalyzeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var client = WebClient.builder()
                .baseUrl(this.urlDownload)
                .defaultHeader("x-api-key", apikey).build();

        var filename = new FileNameRequest();
        filename.setFilename("test.jpg");

        var result = client.post().uri("/epsi/generate-signed-url")
				.bodyValue(filename)
				.retrieve()
				.bodyToMono(SignUrlResponse.class);

        var response = result.block();

        var signedUrl = response.getSignedUrl();
        var fileName = response.getFilename();

        var data = Files.readAllBytes(Path.of(WebclientAnalyzeApplication.class.getResource("/chat.png").toURI()));

        var urlFactory = new DefaultUriBuilderFactory(signedUrl);
        urlFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        var uploadResponse = WebClient.builder()
                .uriBuilderFactory(urlFactory)
                .build()
                .put()
                .contentType(MediaType.parseMediaType("image/png"))
                .bodyValue(data)
                .retrieve()
                .toBodilessEntity()
                .block();

		var detectLabels = client.get()
                .uri(uriBuilder -> uriBuilder.path("/epsi/detect-labels/{url}").build(fileName))
                .retrieve()
                .bodyToMono(DetectLabelResponse.class)
                .block();


        System.out.println(detectLabels);
    }
}

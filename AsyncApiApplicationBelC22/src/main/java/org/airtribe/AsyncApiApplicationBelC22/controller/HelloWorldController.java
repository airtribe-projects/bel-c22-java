package org.airtribe.AsyncApiApplicationBelC22.controller;

import java.util.List;
import org.airtribe.AsyncApiApplicationBelC22.entity.ProductDimension;
import org.airtribe.AsyncApiApplicationBelC22.entity.ProductResult;
import org.airtribe.AsyncApiApplicationBelC22.service.AsyncApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class HelloWorldController {

  @Autowired
  private AsyncApiService _asyncApiService;

  @GetMapping("/helloWorld")
  public String helloWorld() {
    // blocking code
    return "Hello World!";
  }

  // hello -> hello2 -> syncrhonously
  // hello -> hello2 -> asynchronously
  // restTemplate -> synchrnous
  // webClient -> sync and async -> Reactive programmatic
  @GetMapping("/hello2")
  public String helloWorld2() {
    return _asyncApiService.invokeHelloEndpoint();
  }

  // sync -> RestTemplate
  @GetMapping("/products")
  public ProductResult fetchProductsSync() {
    return _asyncApiService.fetchProductsSync();
  }

  // web client async
  @GetMapping("/products/async")
  public Mono<ProductResult> fetchProductsAsync() {
    return _asyncApiService.fetchProductsAsync();
  }

  @GetMapping("/products/async/retry")
  public Mono<ProductResult> fetchProductsAsyncRetry() {
    return _asyncApiService.fetchProductsAsyncRetry();
  }

  // Web client async -> Block
  @GetMapping("/products/sync/webClient")
  public ProductResult fetchProductsSyncWebClient() {
    return _asyncApiService.fetchProductsSyncWebClient();
  }

  // Mono.zip
  @GetMapping("/products/async/parallelAll")
  public Mono<List<ProductResult>> fetchProductsAsyncParallelAll() {
    return _asyncApiService.fetchProductsAsyncParallelAll();
  }

  // Mono.first
  @GetMapping("/products/async/parallelAny")
  public Mono<ProductResult> fetchProductsAsyncParallelAny() {
    return _asyncApiService.fetchProductsAsyncParallelAny();
  }

  // Products API
  // First API
  // Second API
  // Third
  // Result = combination of result1 + result2 + result3
  @GetMapping("/products/chainedSequentialSync")
  public List<ProductResult> fetchProductsChainedSequentialSync() {
    return _asyncApiService.fetchProductsChainedSequentialSync();
  }

  @GetMapping("/products/chainedSequentialAsync")
  public Mono<List<ProductResult>> fetchProductsChainedSequentialAsync() {
    return _asyncApiService.fetchProductsChainedSequentialAsync();
  }

  @GetMapping(value = "/products/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ProductResult> getProductsFluxStream() {
    System.out.println("Thread handling the request " + Thread.currentThread().getName());
    return _asyncApiService.fetchProductsFluxStream();
  }

  @ExceptionHandler({WebClientException.class, Exception.class})
  public ResponseEntity handleWebClientException(Exception ex) {
    System.err.println("Exception occurred: " + ex.getMessage());
    return ResponseEntity.status(500).body("An error occurred while processing the request: " + ex.getMessage());
  }

}

// latency
// Sync api invocation -> External API provider
// Amazon -> Visa
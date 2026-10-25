package org.airtribe.AsyncApiApplicationBelC22.service;

import java.time.Duration;
import java.util.List;
import org.airtribe.AsyncApiApplicationBelC22.entity.ProductResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


@Service
public class AsyncApiService {

  @Autowired
  private RestTemplate _restTemplate;

  @Autowired
  private WebClient _webClient;

  // restTemplate
  public String invokeHelloEndpoint() {
    String result = _restTemplate.getForObject("http://localhost:8080/helloWorld", String.class);
    return result;
  }

  public ProductResult fetchProductsSync() {
    return _restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);
  }

  public Mono<ProductResult> fetchProductsAsync() {
   Mono<ProductResult> productResultMono =  _webClient.get().uri("https://dummyjson.com.com/fhjfhkdjfhd")
       .retrieve().onStatus(status -> !status.is2xxSuccessful(), clientResponse -> {
         System.out.println("Error occurred while fetching products: " + clientResponse.statusCode());
         return Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error occurred while fetching products: " + clientResponse.statusCode()));
       }).
       bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    });

    for (int i = 0; i < 1000; i++) {
      // Simulating some processing
      System.out.println("Processing item " + i + " on thread: " + Thread.currentThread().getName());
    }

    return productResultMono;
  }

  public ProductResult fetchProductsSyncWebClient() {
    ProductResult result = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    }).block();

    for (int i = 0; i < 1000; i++) {
      // Simulating some processing
      System.out.println("Processing item " + i + " on thread: " + Thread.currentThread().getName());
    }


    return result;
  }

  public Mono<List<ProductResult>> fetchProductsAsyncParallelAll() {

    Mono<ProductResult> result1 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    });

    Mono<ProductResult> result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    });

    Mono<ProductResult> result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    }).onErrorResume(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
      return Mono.just(new ProductResult(List.of()));
    });

    return Mono.zip(result1, result2, result3).map(tuple -> List.of(tuple.getT1(), tuple.getT2(), tuple.getT3()));
  }

  public Mono<ProductResult> fetchProductsAsyncParallelAny() {
    Mono<ProductResult> result1 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    });

    Mono<ProductResult> result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    });

    Mono<ProductResult> result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    }).onErrorResume(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
      return Mono.just(new ProductResult(List.of()));
    });

    return Mono.firstWithValue(result1, result2, result3);

  }

  public List<ProductResult> fetchProductsChainedSequentialSync() {
    // Tomcat server tread
    System.out.println("Thread handling the request " + Thread.currentThread().getName());
    // rest template or web client ?
    ProductResult result1 = _restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);

    // tomcat server thread
    System.out.println("Thread handling the request " + Thread.currentThread().getName());
    ProductResult result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).block();


    System.out.println("Thread handling the request " + Thread.currentThread().getName());
    ProductResult result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).block();

    return List.of(result1, result2, result3);
  }

  public Mono<List<ProductResult>> fetchProductsChainedSequentialAsync() {
    // Tomcat thread
    System.out.println("Thread handling the request " + Thread.currentThread().getName());

    Mono<List<ProductResult>> results =  _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    }).flatMap(result1 -> {
      return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
        System.out.println("Thread handling the request " + Thread.currentThread().getName());
      }).doOnError(error -> {
        System.out.println("Error occurred while fetching products: " + error.getMessage());
      }).doOnSubscribe(subscription -> {
        System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
      }).flatMap(result2 -> {
        return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
          System.out.println("Thread handling the request " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred while fetching products: " + error.getMessage());
        }).doOnSubscribe(subscription -> {
          System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
        }).map(result3 -> List.of(result1, result2, result3));
      });
    });

    for (int i = 0; i < 1000; i++) {
      // Simulating some processing
      System.out.println("Processing item " + i + " on thread: " + Thread.currentThread().getName());
    }

    return results;
  }

  public Flux<ProductResult> fetchProductsFluxStream() {
    return Flux.interval(Duration.ofSeconds(5)).flatMap(result -> {
      return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
        System.out.println("Thread handling the request " + Thread.currentThread().getName());
      }).doOnError(error -> {
        System.out.println("Error occurred while fetching products: " + error.getMessage());
      }).doOnSubscribe(subscription -> {
        System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
      });
    });
  }

  public Mono<ProductResult> fetchProductsAsyncRetry() {
    return _webClient.get().uri("https://dummyjsonfhjfheifhfier.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(productResult -> {
      System.out.println("Thread handling the request " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred while fetching products: " + error.getMessage());
    }).doOnSubscribe(subscription -> {
      System.out.println("Subscription started on thread: " + Thread.currentThread().getName());
    }).retryWhen(Retry.backoff(2, Duration.ofSeconds(2)).filter(throwable -> {
      if (throwable instanceof WebClientException) {
        System.out.println("Retrying due to WebClientException: " + throwable.getMessage());
        return true;
      } else {
        System.out.println("Not retrying due to non-WebClientException: " + throwable.getMessage());
        return false;
      }
    }).doBeforeRetry(retrySignal -> {
      System.out.println("Retrying product GET, retry " + (retrySignal.totalRetries() + 1));
    }).onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()));
  }
}

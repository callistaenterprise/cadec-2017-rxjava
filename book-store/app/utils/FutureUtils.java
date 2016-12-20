package utils;

import io.reactivex.Single;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FutureUtils {

	public static <T> Single<T> toSingle(CompletionStage<T> future) {
		return Single.create(subscriber ->
				future.whenComplete((result, error) -> {
					if (error != null) {
						subscriber.onError(error);
					} else {
						subscriber.onSuccess(result);
					}
				}));
	}

	public static <T> CompletableFuture<T> fromSingle(Single<T> single) {
		final CompletableFuture<T> future = new CompletableFuture<>();
		single
				.doOnError(future::completeExceptionally)
				.subscribe(future::complete);
		return future;
	}
}

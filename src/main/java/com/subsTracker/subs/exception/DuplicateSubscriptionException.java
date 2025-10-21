package com.subsTracker.subs.exception;

/**
 * Kullanıcı aynı servisi ikinci kez eklemeye çalıştığında fırlatılan özel hata.
 */
public class DuplicateSubscriptionException extends RuntimeException {
    public DuplicateSubscriptionException(String message) {
        super(message);
    }
}

package com.porodkin.timelogger.usecase;

/**
 * A generic interface for presenting work session data or related objects.
 *
 * <p>This interface defines a contract for presenting data of a specified type {@code T}.
 * Implementations of this interface are responsible for defining how the data is displayed,
 * processed, or otherwise presented to the user or another system component.
 *
 * @param <T> the type of data to be presented by the implementation.
 */
public interface WorkSessionPresenter<T> {

    /**
     * Presents the specified data of type {@code T}.
     *
     * <p>Implementations of this method determine the way the provided data
     * is displayed, processed, or otherwise utilized.
     *
     * @param t the data to be presented. Must not be null.
     * @throws IllegalArgumentException if the provided {@code t} is null.
     */
    void present(T t);
}

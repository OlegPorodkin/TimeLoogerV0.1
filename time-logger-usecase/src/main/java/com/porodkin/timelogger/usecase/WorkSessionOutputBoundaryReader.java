package com.porodkin.timelogger.usecase;

import com.porodkin.timelogger.usecase.datastuct.output.WorkSessionReceivedOutputData;

/**
 * A specialized interface for reading and presenting work session output boundary data.
 *
 * <p>This interface extends {@link WorkSessionPresenter} to include functionality for retrieving
 * processed output data of type {@code R} while presenting {@link WorkSessionReceivedOutputData}.
 * Implementations are responsible for defining how work session data is presented and how
 * the resulting processed data is accessed.
 *
 * @param <R> the type of data returned by the {@link #getData()} method.
 */
public interface WorkSessionOutputBoundaryReader<R> extends WorkSessionPresenter<WorkSessionReceivedOutputData> {

    /**
     * Retrieves the processed data of type {@code R}.
     *
     * <p>This method provides access to the processed output data after it has been
     * presented through {@link WorkSessionPresenter#present(WorkSessionReceivedOutputData)}.
     *
     * @return the processed data of type {@code R}.
     * @throws IllegalStateException if the data is not available or has not been processed yet.
     */
    R getData();
}

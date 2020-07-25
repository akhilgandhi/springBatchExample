package com.example.batch.processor;

import com.example.batch.model.InputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class BatchDataItemProcessor implements ItemProcessor<InputData, InputData> {

    private static final Logger log = LoggerFactory.getLogger(BatchDataItemProcessor.class);

    @Override
    public InputData process(final InputData inputData) throws Exception {

        final String prodName = inputData.getProductName().toLowerCase().trim();

        final InputData transformedData = new InputData();

        transformedData.setId(inputData.getId());
        transformedData.setSubscriberId(inputData.getSubscriberId());
        transformedData.setProductName(prodName);
        transformedData.setAmount(inputData.getAmount());

        log.info("Converting ( " + inputData + " ) into ( " + transformedData + " )");

        return transformedData;
    }
}

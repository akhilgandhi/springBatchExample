package com.example.batch;

import com.example.batch.listener.JobCompletionNotificationListener;
import com.example.batch.model.InputData;
import com.example.batch.processor.BatchDataItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;

import javax.sql.DataSource;
import java.net.MalformedURLException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    @Bean
    @StepScope
    public FlatFileItemReader<InputData> reader(@Value("#{jobParameters[inputFileUrl]}") String fileUrl) throws MalformedURLException {
        return new FlatFileItemReaderBuilder<InputData>()
                .name("batchDataItemReader")
                .resource(new FileUrlResource(fileUrl))
                .linesToSkip(1)
                .delimited()
                .names(new String[]{"id","subscriber_Id","product_name","amount"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<InputData>() {{
                    setTargetType(InputData.class);
                }})
                .build();

    }

    @Bean
    public BatchDataItemProcessor processor() {
        return new BatchDataItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<InputData> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<InputData>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO springBatchData (data_id, subscriber_id, prod_name, amount) values (:id, :subscriberId, :productName, :amount)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importBatchDataJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importBatchDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<InputData> writer) throws MalformedURLException {
        return stepBuilderFactory.get("step1")
                .<InputData, InputData> chunk(5)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .processor(processor())
                .writer(writer)
                .build();
    }
}

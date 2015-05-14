package com.zqh.hadoop.mr.secondary;

import com.zqh.hadoop.MapReduceTester;
import org.junit.Test;
import org.apache.hadoop.fs.Path;
import java.util.List;
import java.util.Arrays;
import org.junit.Assert;
import static org.hamcrest.core.Is.is;

/**
 * Unit test for Left Outer Join App
 */
public class RawMapreduceTest {

    @Test
    public void integrationTest() throws Exception {
        final MapReduceTester runner = new MapReduceTester();

        MapReduceTester.TestJob job = new MapReduceTester.TestJob(){
            @Override
            public void run(MapReduceTester.JobArgs args) throws Exception {
                String usersString =
                        "1\tmatthew@test.com\tEN\tUS\n"
                                + "2\tmatthew@test2.com\tEN\tGB\n"
                                + "3\tmatthew@test3.com\tFR\tFR";

                String transactionString =
                        "1\t1\t1\t300\ta jumper\n"
                                + "2\t1\t2\t300\ta jumper\n"
                                + "3\t1\t2\t300\ta jumper\n"
                                + "4\t2\t3\t100\ta rubber chicken\n"
                                + "5\t1\t3\t300\ta jumper";

                Path transactions = runner.registerString(transactionString);
                Path users = runner.registerString(usersString);
                Path staging = runner.registerDirectory(false);
                Path output = runner.registerDirectory(false);

                RawMapreduce.main(args.conf, transactions, users, staging, output);

                List<String> results = runner.collectStrings(output);
                Assert.assertEquals("number of results is correct", results.size(), 2);
                List<String> expected = Arrays.asList("1\t3", "2\t1");
                Assert.assertThat(results, is(expected));
            }
        };
        runner.run(job);
    }

}

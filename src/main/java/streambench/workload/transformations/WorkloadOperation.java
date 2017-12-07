package streambench.workload.transformations;

import org.apache.samza.SamzaException;
import org.apache.samza.operators.KV;
import org.apache.samza.operators.MessageStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streambench.workload.pojo.WorkloadTransformation;

import java.util.List;

public abstract class WorkloadOperation {

    private static final Logger logger = LoggerFactory.getLogger(WorkloadOperation.class);

    protected static final long SLEEP_DURATION = 500; // 0.5 second
    protected static final double SLEEP_LOAD = 0.8;

    protected WorkloadTransformation transformation;

    public static List<MessageStream<KV<String, String>>> apply(WorkloadTransformation transformation, MessageStream<KV<String, String>> srcStream) {
        switch (transformation.getOperator()) {
            case "filter": return new FilterOp(transformation).apply(srcStream);
            case "split": return new SplitOp(transformation).apply(srcStream);
        }

        logger.error("Unknown operator: " + transformation.getOperator());
        throw new SamzaException("Unknown operator: " + transformation.getOperator());
    }

    public WorkloadOperation(WorkloadTransformation transformation) {
        this.transformation = transformation;
    }

    public abstract List<MessageStream<KV<String, String>>> apply(MessageStream<KV<String, String>> srcStream);
}

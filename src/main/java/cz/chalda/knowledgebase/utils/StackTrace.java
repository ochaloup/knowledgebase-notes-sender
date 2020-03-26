package cz.chalda.knowledgebase.utils;

import cz.chalda.knowledgebase.execution.ExecutionContext;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public final class StackTrace {
    private StackTrace() {
        throw new IllegalStateException("private constructor for utility class");
    }
    private static final String NEW_LINE = String.format("%n");

    public static String getStackTrace() {
        return getStackTrace(Long.MAX_VALUE);
    }

    public static String getStackTrace(long limit) {
        return StackWalker.getInstance()
                .walk(s -> s.limit(limit))
                .collect(StringBuilder::new,
                        (sb,sf) -> sb.append(sf.toString()).append(NEW_LINE),
                        StringBuilder::append)
                .toString();
    }

    public static String exceptionToStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

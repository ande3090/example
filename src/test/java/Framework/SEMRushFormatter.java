package Framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class SEMRushFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        sb.append(dateFormat.format(date) + " ");
        sb.append(record.getLevel()).append(':');
        sb.append(record.getMessage()).append('\n');
        return sb.toString();
    }
}

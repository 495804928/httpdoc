package io.httpdoc.core.fragment;

import java.io.IOException;

/**
 * Appendable 拼接器
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-04-28 11:06
 **/
public class AppendableAppender extends AbstractAppender<AppendableAppender> implements Appender<AppendableAppender> {
    private final Appendable appendable;

    public AppendableAppender(Appendable appendable) {
        this.appendable = appendable;
    }

    @Override
    public AppendableAppender append(CharSequence text) throws IOException {
        appendable.append(text);
        return this;
    }

    @Override
    public void flush() throws IOException {
    }
}
package org.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CounterFilter implements Filter {

    public static final String _HEADER_CONC_PEEK_PRINT = "X-CONC-PRINT";
    public static final String _HEADER_CONC_PEEK_RES = "X-CONC-PEEK";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        if (req instanceof HttpServletRequest) {
            if (null != ((HttpServletRequest) req).getHeader(_HEADER_CONC_PEEK_PRINT)) {
                long peek = PeekCounter.peek();
                System.out.println("### concurrency :: peek=" + peek);
                if (res instanceof HttpServletResponse) {
                    ((HttpServletResponse) res).setHeader(_HEADER_CONC_PEEK_RES, String.valueOf(peek));
                    return;
                }
            }
        }

        try {
            PeekCounter.increment();
            chain.doFilter(req, res);

        } finally {
            PeekCounter.decrement();
        }

    }

}

package com.leet;

import java.util.ArrayList;
import java.util.List;

/**
 * @link：https://leetcode-cn.com/problems/design-browser-history/
 */
public class BrowserHistory {
    private List<String> history;
    private int now;
    private int end;

    public BrowserHistory(String homepage) {
        this.history = new ArrayList<String>();
        this.now = 0;
        this.end = 0;
        this.history.add(this.now, homepage);
    }

    public void visit(String url) {
        this.now++;
        this.history.add(this.now, url);
        this.end = this.now;
    }

    public String back(int steps) {
        this.now = Math.max((this.now - steps), 0);
        return this.history.get(this.now);
    }

    public String forward(int steps) {
        this.now = Math.min((this.now + steps), this.end);
        return this.history.get(this.now);
    }
}

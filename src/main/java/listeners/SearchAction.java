package listeners;

import view.Canvas;

public class SearchAction implements Runnable {
    private final Canvas canvas;

    public SearchAction(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void run() {
        canvas.search();
    }
}
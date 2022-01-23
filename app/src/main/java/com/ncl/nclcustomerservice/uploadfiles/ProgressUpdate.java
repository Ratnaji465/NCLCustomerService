package com.ncl.nclcustomerservice.uploadfiles;

public class ProgressUpdate {
    private int previousProgress;
   private double currentProgress;

    public ProgressUpdate(int previousProgress, double currentProgress) {
        this.previousProgress = previousProgress;
        this.currentProgress = currentProgress;
    }

    public ProgressUpdate(double currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getPreviousProgress() {
        return previousProgress;
    }

    public double getCurrentProgress() {
        return currentProgress;
    }

    public void setPreviousProgress(int previousProgress) {
        this.previousProgress = previousProgress;
    }

    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = currentProgress;
    }

    @Override
    public String toString() {
        return "ProgressUpdate{" +
                "previousProgress=" + previousProgress +
                ", currentProgress=" + currentProgress +
                '}';
    }
}

package yj.p.macaron.add;

public class Work_date {
    int year;
    int month;
    int date;
    String worker;

    public Work_date(String worker) {
        this.worker = worker;
    }

    public Work_date(int year, int month, int date, String worker) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.worker = worker;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}

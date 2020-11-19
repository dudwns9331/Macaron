package yj.p.macaron.add;

public class Work_date implements Comparable<Work_date> {
    int year;
    int month;
    int date;
    String worker;
    String work_time;
    String d_worker;
    String d_work_time;

    public Work_date(String worker) {
        this.worker = worker;
    }

    public Work_date(int year, int month, int date, String worker) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.worker = worker;
        this.d_worker = "";
        this.d_work_time = "";
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
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

    public void addWorker(String worker) {this.d_worker = this.d_worker + worker + "\n";}

    public void addwork_time(String work_time) {this.d_work_time = d_work_time + work_time + "\n"; }

    public String getWork_timeall() { return d_work_time; }

    public String getWorkerall() {return d_worker;}

    @Override
    public int compareTo(Work_date work_date) {
        return Integer.compare(this.date, work_date.date);
    }
}

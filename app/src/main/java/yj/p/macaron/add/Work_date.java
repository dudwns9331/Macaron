package yj.p.macaron.add;

public class Work_date implements Comparable<Work_date> {
    int year; // 년
    int month; // 월
    int date; // 일

    /**
     * 객체마다 저장되는 데이터 -> "d_worker" 근무자 + "\n" 형식
     *                        -> "d_work_time" 0시 0분 ~ 0시 0분 + "\n"
     */

    String worker;      // 근무자
    String work_time;       // 근무 시간
    String d_worker;        // 모든 근무자
    String d_work_time;     // 모든 근무 시간


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

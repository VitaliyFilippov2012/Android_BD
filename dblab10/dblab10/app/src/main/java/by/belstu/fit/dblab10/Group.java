package by.belstu.fit.dblab10;

public class Group {
    public int IDGROUP,COURSE;
    public String NAME,FACULTY,HEAD;


    public Group(int IDGROUP, String FACULTY, String NAME, String HEAD,int COURSE) {
        this.IDGROUP=IDGROUP;
        this.NAME=NAME;
        this.FACULTY=FACULTY;
        this.HEAD=HEAD;
        this.COURSE=COURSE;
    }
}

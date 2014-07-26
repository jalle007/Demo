package mycalories.com.jalle.mycalories;

import java.util.Date;

//  this class is not used

public class MealClass {
    public String user;
    public String description;
    public int calories;
    public Date date;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
     public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}

package Modele;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

/**
 * Classe représentant une date personnalisée (année, mois, jour, heure, minute) et permettant de faire des opérations dessus
 */
public class CustomDateTime implements Comparable<CustomDateTime> {


    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;



    /**
     * Constructeur par défaut
     * @param year année
     * @param month mois
     * @param day jour
     * @param hour heure
     * @param minute minute
     * @throws IllegalArgumentException si la date est invalide
     * (année négative, mois négatif ou supérieur à 12, jour négatif ou supérieur à 31, heure négative ou supérieure à 24,
     * minute négative ou supérieure à 60)
     */
    public CustomDateTime(int year, int month, int day, int hour, int minute) throws IllegalArgumentException{
        if (year < 0 || month < 0 || month > 12 || day < 0 || day > 31 || hour < 0 || hour > 24 || minute < 0 || minute > 60) {
            throw new IllegalArgumentException("Date invalide");
        }
        setYear(year);
        setMonth(month);
        setDay(day);
        setHour(hour);
        setMinute(minute);
    }

    /**
     * Constructeur depuis un LocalDateTime
     * @param dateTime DateTime
     * @see LocalDateTime
     */
    public CustomDateTime(LocalDateTime dateTime) {
        setYear(dateTime.getYear());
        setMonth(dateTime.getMonthValue());
        setDay(dateTime.getDayOfMonth());
        setHour(dateTime.getHour());
        setMinute(dateTime.getMinute());
    }

    /**
     * Constructeur depuis un LocalDate et un LocalTime
     * @param date LocalDate
     * @param time LocalTime
     * @see LocalDate
     * @see LocalTime
     */
    public CustomDateTime(LocalDate date, LocalTime time) {
    	setYear(date.getYear());
        setMonth(date.getMonthValue());
        setDay(date.getDayOfMonth());
        setHour(time.getHour());
        setMinute(time.getMinute());
    }

    /**
     * Constructeur depuis un LocalDate
     * @param date LocalDate
     * @see LocalDate
     */
    public CustomDateTime(LocalDate date) {
        	setYear(date.getYear());
            setMonth(date.getMonthValue());
            setDay(date.getDayOfMonth());
            setHour(0);
            setMinute(0);
    }

    /**
     * Getter de l'année
     * @return l'année
     */
    public int getYear() {
        return Integer.parseInt(year);
    }

    /**
     * Setter de l'année
     * @param year l'année
     */
    public void setYear(int year) {
        this.year = String.valueOf(year);
    }

    /**
     * Ajoute des années à la date
     * @param nbAnnee nombre d'années à ajouter
     */
    public void addYear(int nbAnnee) {
        this.setYear(this.getYear() + nbAnnee);
    }

    /**
     * Ajoute des années à la date
     * @param nbAnnee nombre d'années à ajouter
     * @return une nouvelle date avec le nombre d'années ajoutées
     */
    public CustomDateTime plusYear(int nbAnnee) {
        CustomDateTime copy = new CustomDateTime(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute());
        copy.addYear(nbAnnee);
        return copy;
    }

    /**
     * Getter du mois
     * @return le mois
     */
    public int getMonth() {
        return Integer.parseInt(month);
    }

    /**
     * Setter du mois
     * @param month le mois
     */
    public void setMonth(int month) {
        if (month < 10) {
            this.month = "0" + month;
        } else {
            this.month = String.valueOf(month);
        }
    }

    /**
     * Ajoute des mois à la date
     * @param month nombre de mois à ajouter
     */
    public void addMonth(int month) {
        int m = this.getMonth() + month;
        if (m < 0) {
            this.setMonth(12 + m);
            this.addYear(-1);
        } else if (m > 12) {
            this.setMonth(m - 12);
            this.addYear(1);
        } else {
            this.setMonth(m);
        }
    }

    /**
     * Ajoute des mois à la date
     * @param month nombre de mois à ajouter
     * @return une nouvelle date avec le nombre de mois ajoutés
     */
    public CustomDateTime plusMonth(int month) {
        CustomDateTime copy = new CustomDateTime(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute());
        copy.addMonth(month);
        return copy;
    }

    /**
     * Getter du jour
     * @return le jour
     */
    public int getDay() {
        return Integer.parseInt(day);
    }

    /**
     * Setter du jour
     * @param day le jour
     */
    public void setDay(int day) {
        if (day < 10) {
            this.day = "0" + day;
        } else {
            this.day = String.valueOf(day);
        }
    }

    /**
     * Ajoute des jours à la date
     * @param day nombre de jours à ajouter
     */
    public void addDay(int day) {
        int d = this.getDay() + day;
        if (d < 0) {
            this.setDay(31 + d);
            this.addMonth(-1);
        } else if (d > 31) {
            this.setDay(d - 31);
            this.addMonth(1);
        } else {
            this.setDay(d);
        }
    }

    /**
     * Ajoute des jours à la date
     * @param day nombre de jours à ajouter
     * @return une nouvelle date avec le nombre de jours ajoutés
     */
    public CustomDateTime plusDay(int day) {
        CustomDateTime copy = new CustomDateTime(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute());
        copy.addDay(day);
        return copy;
    }

    /**
     * Getter de l'heure
     * @return l'heure
     */
    public int getHour() {
        return Integer.parseInt(hour);
    }

    /**
     * Setter de l'heure
     * @param hour l'heure
     */
    public void setHour(int hour) {
        if (hour < 10) {
            this.hour = "0" + hour;
        } else {
            this.hour = String.valueOf(hour);
        }
    }

    /**
     * Ajoute des heures à la date
     * @param hour nombre d'heures à ajouter
     */
    public void addHour(int hour) {
        int h = this.getHour() + hour;
        if (h < 0) {
            this.setHour(24 + h);
            this.addDay(-1);
        } else if (h > 24) {
            this.setHour(h - 24);
            this.addDay(1);
        } else {
            this.setHour(h);
        }
    }

    /**
     * Ajoute des heures à la date
     * @param hour nombre d'heures à ajouter
     * @return une nouvelle date avec le nombre d'heures ajoutées
     */
    public CustomDateTime plusHour(int hour) {
        CustomDateTime copy = new CustomDateTime(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute());
        copy.addHour(hour);
        return copy;
    }

    /**
     * Getter de la minute
     * @return la minute
     */
    public int getMinute() {
        return Integer.parseInt(minute);
    }

    /**
     * Setter de la minute
     * @param minute la minute
     */
    public void setMinute(int minute) {
        if (minute < 10) {
            this.minute = "0" + minute;
        } else {
            this.minute = String.valueOf(minute);
        }
    }

    /**
     * Ajoute des minutes à la date
     * @param minute nombre de minutes à ajouter
     */
    public void addMinute(int minute) {
        int m = this.getMinute() + minute;
        if (m < 0) {
            this.setMinute(60 + m);
            this.addHour(-1);
        } else if (m > 60) {
            this.setMinute(m - 60);
            this.addHour(1);
        } else {
            this.setMinute(m);
        }
    }

    /**
     * Ajoute des minutes à la date
     * @param minute nombre de minutes à ajouter
     * @return une nouvelle date avec le nombre de minutes ajoutées
     */
    public CustomDateTime plusMinute(int minute) {
        CustomDateTime copy = new CustomDateTime(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute());
        copy.addMinute(minute);
        return copy;
    }

    /**
     * Ajoute une autre date à la date
     * @param other autre date
     * @return la date avec l'autre date ajoutée
     */
    public CustomDateTime add(CustomDateTime other) {
        this.plusYear(other.getYear());
        this.plusMonth(other.getMonth());
        this.plusDay(other.getDay());
        this.plusHour(other.getHour());
        this.plusMinute(other.getMinute());
        return this;
    }

    /**
     * Soustrait une autre date à la date
     * @param other autre date
     * @return une nouvelle date avec l'autre date ajoutée
     */
    public CustomDateTime minus(CustomDateTime other) {
        this.plusYear(-other.getYear());
        this.plusMonth(-other.getMonth());
        this.plusDay(-other.getDay());
        this.plusHour(-other.getHour());
        this.plusMinute(-other.getMinute());
        return this;
    }

    /**
     * Teste si la date est après une autre
     * @param other autre date
     * @return true si other est après la date, false sinon
     */
    public boolean isAfter(CustomDateTime other) {
        if (this.getYear() > other.getYear()) {
            return true;
        } else if (this.getYear() == other.getYear()) {
            if (this.getMonth() > other.getMonth()) {
                return true;
            } else if (this.getMonth() == other.getMonth()) {
                if (this.getDay() > other.getDay()) {
                    return true;
                } else if (this.getDay() == other.getDay()) {
                    if (this.getHour() > other.getHour()) {
                        return true;
                    } else if (this.getHour() == other.getHour()) {
                        if (this.getMinute() > other.getMinute()) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Teste si la date est avant une autre
     * @param other autre date
     * @return true si other est avant la date, false sinon
     */
    public boolean isBefore(CustomDateTime other) {
        if (this.getYear() < other.getYear()) {
            return true;
        } else if (this.getYear() == other.getYear()) {
            if (this.getMonth() < other.getMonth()) {
                return true;
            } else if (this.getMonth() == other.getMonth()) {
                if (this.getDay() < other.getDay()) {
                    return true;
                } else if (this.getDay() == other.getDay()) {
                    if (this.getHour() < other.getHour()) {
                        return true;
                    } else if (this.getHour() == other.getHour()) {
                        if (this.getMinute() < other.getMinute()) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Teste si la date est égale à une autre
     * @param other autre date
     * @return true si other est égale à la date, false sinon
     */
    public boolean isEquals(CustomDateTime other) {
        return this.getYear() == other.getYear() && this.getMonth() == other.getMonth() && this.getDay() == other.getDay() && this.getHour() == other.getHour() && this.getMinute() == other.getMinute();
    }

    /**
     * Teste si la date est entre deux autres dates
     * @param other1 autre date
     * @param other2 autre date
     * @return true si la date est entre other1 et other2, false sinon
     * @see CustomDateTime#isAfter(CustomDateTime)
     * @see CustomDateTime#isBefore(CustomDateTime)
     */
    public boolean isBetween(CustomDateTime other1, CustomDateTime other2) {
        return this.isAfter(other1) && this.isBefore(other2);
    }

    /**
     * Teste si la date est entre deux autres dates (LocalDate)
     * @param other1 autre date LocalDate
     * @param other2 autre date LocalDate
     * @return true si la date est entre other1 et other2, false sinon
     * @see LocalDate
     * @see CustomDateTime#isAfter(CustomDateTime)
     * @see CustomDateTime#isBefore(CustomDateTime)
     */
    public boolean isBetween(LocalDate other1, LocalDate other2) {
        return this.isAfter(new CustomDateTime(other1.atStartOfDay())) && this.isBefore(new CustomDateTime(other2.atTime(23, 59, 59)));
    }

    /**
     * Affiche la date
     * @return la date au format "dd-mm-yyyy"
     */
    public String getDate() {
        return day + "-" + month + "-" + year;
    }

    /**
     * Affiche la date au format SQL
     * @return la date au format "yyyy-mm-dd"
     */
    public String getDateSQL() {
    	return year + "-" + month + "-" + day;
    }

    /**
     * Affiche l'heure
     * @return l'heure au format "hh:mm:ss.0"
     */
    public String getTime() {
        return hour + ":" + minute + ":00.0";
    }

    /**
     * Teste si deux objets dates sont identiques
     * @param o autre objet
     * @return true si les deux objets sont identiques, false sinon
     * @see Objects#equals(Object, Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomDateTime that = (CustomDateTime) o;
        return getYear() == that.getYear() && getMonth() == that.getMonth() && getDay() == that.getDay() && getHour() == that.getHour() && getMinute() == that.getMinute();
    }

    /**
     * Hashcode de la date
     * @return le hashcode de la date
     * @see Objects#hash(Object...)
     */
    @Override
    public int hashCode() {
        return Objects.hash(year, month, day, hour, minute);
    }

    /**
     * Affiche la date complète
     * @return la date au format "yyyy-mm-dd hh:mm:ss.0"
     */

    public String formatSQL() {
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00.0";
    }

    /**
     * Affiche la date complète
     * @return la date au format "dd-mm-yyyy hh:mm"
     */
    @Override
    public String toString() {
        return LocalDateTime.of(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    /**
     * Compare deux dates
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
     */
    @Override
    public int compareTo(CustomDateTime o) {
        if (this.isBefore(o)) {
            return -1;
        } else if (this.isAfter(o)) {
            return 1;
        } else return 0;
    }

    /**
     * Affiche la date au format "dd-mm-yyyy"
     * @return la date au format "dd-mm-yyyy"
     */
    public String format() {
    	return  LocalDateTime.of(this.getYear(), this.getMonth(), this.getDay(), this.getHour(), this.getMinute()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }
}

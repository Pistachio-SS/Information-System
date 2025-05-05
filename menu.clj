(ns menu
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [db]))


(defn showMenu []
  (println "\n\n*** Grade Processing Menu ***")
  (println "----------------------------\n")
  (println "1. List Names")
  (println "2. Display Student Grades by ID")
  (println "3. Display Student Records by Last Name")
  (println "4. Display Component Grades + Average")
  (println "5. Display Final Grades and Class Average")
  (println "6. Exit")
  (print "\nEnter an option? ")
  (flush)
  (read-line))


(defn option1 [students]
  (println "\nList of Students:\n")
  (doseq [student students]
    (println (str (:id student) " - " (:first-name student) " " (:last-name student)))))


(defn option2 [students]
  (print "\nEnter Student ID: ")
  (flush)
  (let [input-id (read-line)
        student (first (filter #(= (:id %) input-id) students))]
    (if student
      (do
        (println (str "\nGrades for " (:first-name student) " " (:last-name student) ":"))
        (doseq [[k v] (:grades student)]
          (println (str k ": " (:grade v) " (Weight: " (:weight v) ")"))))
      (println "\nStudent not foundd."))))


(defn option3 [students]
  (print "\nEnter the Last Name: ")
  (flush)
  (let [lname-input (read-line)
        matches (filter #(= (str/lower-case (:last-name %)) (str/lower-case lname-input)) students)]
    (if (seq matches)
      (do
        (println "\nFound:\n")
        (doseq [s matches]
          (println
           (str "[" (:id s) " " (:first-name s) " " (:last-name s) " "
                (:grades s) "]")))
        (println (str "\nTotal " (count matches) " record(s) found.")))
      (println "\nNo records found."))))


(defn option4 [students]
  (print "\nEnter Component Name: ")
  (flush)
  (let [component-input (read-line)
        component (keyword component-input)
        student-grades (for [s students
                             :let [entry (get-in s [:grades component])]
                             :when entry]
                         {:id (:id s)
                          :grade (:grade entry)})
        grades (map :grade student-grades)]
    (if (empty? student-grades)
      (println "\nNo data found for component:" component-input)
      (do
        (doseq [{:keys [id grade]} student-grades]
          (println (str "(" id " " grade ")")))
        (let [avg (/ (reduce + grades) (count grades))
              avg-rounded (/ (Math/round (* avg 10.0)) 10.0)]
          (println (str "Average: " avg-rounded)))))))


(defn option5 [students]
  (let [desired-order ["A1" "A2" "MIDTERM" "FINAL"]
        components (map keyword desired-order)
        sorted-students (sort-by #(Long/parseLong (:id %)) students)
        rows
        (for [s sorted-students]
          (let [grades (:grades s)
                row-grades (map #(get-in grades [% :grade] 0.0) components)
                weights (map #(get-in grades [% :weight] 0.0) components)
                total (if (pos? (reduce + weights))
                        (/ (reduce + (map * row-grades weights))
                           (reduce + weights))
                        0.0)]
            {:id (:id s)
             :grades row-grades
             :total total}))
        comp-averages
        (map (fn [idx]
               (let [vals (map #(nth (:grades %) idx) rows)]
                 (/ (reduce + vals) (count vals))))
             (range (count components)))
        total-avg (/ (reduce + (map :total rows)) (count rows))]
    
    (println (str "(STUDID " (str/join " " desired-order) " TOTAL)"))
    
    (doseq [{:keys [id grades total]} rows]
      (println (str "(" id " "
                    (str/join " " (map #(format "%.1f" %) grades))
                    " " (format "%.1f" total) ")")))

    ;Averages
    (println (str "(AVG "
                  (str/join " " (map #(format "%.1f" %) comp-averages))
                  " " (format "%.1f" total-avg) ")"))))


(defn processOption [option students]
  (case option
    "1" (option1 students)
    "2" (option2 students)
    "3" (option3 students)
    "4" (option4 students)
    "5" (option5 students)
    "6" (println "\nGood Bye\n")
    (println "Invalid Option, please try again")))


(defn menu [students]
  (loop []
    (let [option (str/trim (showMenu))]
      (if (= option "6")
        (processOption option students)
        (do
          (processOption option students)
          (recur))))))

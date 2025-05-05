(ns db
  (:require [clojure.string :as str]))

(defn parse-student-line [line]
  (let [tokens (str/split line #",")
        id (first tokens)
        first-name (second tokens)
        last-name (nth tokens 2)
        rest (drop 3 tokens)
        grade-triples (partition 3 rest)
        grades (into {}
                     (map (fn [[label weight grade]]
                            [(keyword label)
                             {:weight (Double/parseDouble weight)
                              :grade (Double/parseDouble grade)}])
                          grade-triples))]
    {:id id
     :first-name first-name
     :last-name last-name
     :grades grades}))

(defn load-students [filename]
  (let [lines (str/split-lines (slurp filename))]
    (map parse-student-line lines)))
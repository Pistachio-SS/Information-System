(ns app
  (:require [db]
            [menu :as menu]))

(let [gradesDB (db/load-students "grades.txt")]
  (menu/menu gradesDB))

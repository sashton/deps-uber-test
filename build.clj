(ns build
  (:require [clojure.tools.build.api :as b]
            [hf.depstar.uberjar :as depstar]))

(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def uber-file-depstar "uberjar-depstar.jar")
(def uber-file-deps "uberjar-deps.jar")

(defn uber-depstar [_]
  (b/delete {:path uber-file-depstar})
  (time
   (depstar/build-jar {:basis basis
                       :jar uber-file-depstar})))

(defn uber-deps [_]
  (b/delete {:path uber-file-deps})
  (time
   (b/uber {:class-dir class-dir
            :uber-file uber-file-deps
            :basis basis
           ;; One dependency includes /license as a directory.
           ;; Another dependency includes /license as a file.
           ;; Exclude the directory variant
            :exclude ["^license/.*"]})))

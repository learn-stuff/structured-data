(ns structured-data)



(defn do-a-thing [x]
  (let [xx (+ x x)]
    (Math/pow xx xx)))



(defn spiff [v]
  (+ (get v 0) (get v 2)))



(defn cutify [v]
  (conj v "<3"))



(defn spiff-destructuring [v]
  (let [[x _ y] v]
    (+ x y)))



(defn point [x y]
  [x y])



(defn rectangle [bottom-left top-right]
  [bottom-left top-right])



(defn width [rectangle]
  (let [[[bottom _][top _]] rectangle]
    (- top bottom)))



(defn height [rectangle]
  (let [[[_ bottom][_ top]] rectangle]
    (- top bottom)))



(defn square? [rectangle]
  (let [[[x1 y1][x2 y2]] rectangle]
    (= (- x2 x1) (- y2 y1))))



(defn area [rectangle]
  (let [[[x1 y1][x2 y2]] rectangle]
    (* (- x2 x1) (- y2 y1))))



(defn contains-point? [rectangle point]
  (let [[[x1 y1][x2 y2]] rectangle
        [x y] point]
    (and (<= x1 x x2) (<= y1 y y2))))



(defn contains-rectangle? [outer inner]
  (let [[bottom-left top-right] inner]
    (and (contains-point? outer bottom-left)
         (contains-point? outer top-right))))



(defn title-length [book]
  (count (:title book)))



(defn author-count [book]
  (count (:authors book)))



(defn multiple-authors? [book]
  (> (author-count book) 1))



(defn add-author [book new-author]
  (let [new-authors (conj (:authors book) new-author)]
    (assoc book :authors new-authors)))



(defn alive? [author]
  (not (contains? author :death-year)))



(defn element-lengths [collection]
  (map count collection))



(defn second-elements [collection]
  (map second collection))



(defn titles [books]
  (map :title books))



(defn monotonic? [a-seq]
  (or (apply <= a-seq)
      (apply >= a-seq)))



(defn stars [n]
  (apply str (repeat n "*")))



(defn toggle [a-set elem]
  ((if (contains? a-set elem) disj conj)
   a-set elem))



(defn contains-duplicates? [a-seq]
  (not (= (count a-seq) (count (set a-seq)))))



(defn old-book->new-book [book]
  (let [authors-set (set (:authors book))]
    (assoc book :authors authors-set)))



(defn has-author? [book author]
  (contains?
   (:authors (old-book->new-book book))
   author))



(defn authors [books]
  (apply clojure.set/union
         (map (comp :authors old-book->new-book) books)))



(defn all-author-names [books]
  (set (map :name (authors books))))



(defn author->string [author]
  (let [year (if (contains? author :birth-year)
               (str " (" (:birth-year author) " - " (:death-year author) \))
               "")]
    (str (:name author) year)))



(defn authors->string [authors]
  (apply str (interpose ", " (map author->string authors))))



(defn book->string [book]
  (str (:title book) ", written by " (authors->string (:authors book))))



(defn books->string [books]
  (let [quantity (count books)
        quantity-info (if (> quantity 1)
                        (str quantity " books. ")
                        (str quantity " book. "))]
    (if (zero? quantity)
      "No books."
      (str quantity-info
           (apply str (interpose ". " (map book->string books)))
           "."))))


(defn books-by-author [author books]
  (let [with-author #(has-author? % author)]
    (filter with-author books)))



(defn author-by-name [name authors]
  (let [with-name #(= name (:name %))]
    (first (filter with-name authors))))



(defn living-authors [authors]
  (filter alive? authors))



(defn has-a-living-author? [book]
  (not (empty? (living-authors (:authors book)))))




(defn books-by-living-authors [books]
  (filter has-a-living-author? books))

; %________%

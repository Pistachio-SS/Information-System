# Information-System

DESCRIPTION: It’s	time	to	try	a	little	functional	programming.	In	this	case,	
your	job	will	be	to	develop	a	very	simple	information	system	with	Clojure.	REALLY	simple.		In	fact,	
all	it	will	really	do	is	load	data	from	a	disk	file.	This	data	will	then	form	a	gradebook	file	(very	
similar	to	what	you	see	on	Moodle).		The	gradebook contains	one	flat	file	that	contains	a	list	of	all	
students	in	the	class	with	their	grades for	every	assignment/exam	component.
grades.txt:	<student	id,	first	name,	last	name,	[component,	weight,	grade]…>
The	number	of	components	may	vary	from	file	to	file,	however,	it	would	be	the	same	for	all	students	
in	a	single	file.
A	sample	file	is	given	in	the	following:
40543437,John,Doe,A1,10,95.5,A2,10,100,MIDTERM,20,80,FINAL,60,70
40543476,Jane,Doe,A1,10,100,A2,10,100,MIDTERM,20,90,FINAL,60,100
40545276,Michael,J,A1,10,99,A2,10,98,MIDTERM,20,97,FINAL,60,76
40544545,Yo,McLovin,A1,10,70,A2,10,70,MIDTERM,20,60,FINAL,60,60
...
Note	that	no	[syntax]	error	checking	is	required	for	any	of	the	data	files.	You	can	assume	that	they	
have	been	created	properly, and	all	fields	are	present.	Each	field	is	separated	by	a	“,”	and	contains	a	
non-empty	string.
It	is	suggested	that	you	use	a	vector	for	each	student	and	include	all	grade	components	in	a	map.	
The	class	list	is	then	stored	in	a	list.
See	the	mapify function	in	the	attached	code. Using	that	function,	you	may	store	the	grades	for	each	
student	in	a	map.	For	example	the	grade	for	John	Doe	would	be	something	like:
{"A1" {:weight 10, :grade 95.5},
"A2" {:weight 10, :grade 100},
"MIDTERM" {:weight 20, :grade 80},
"FINAL" {:weight 60, :grade 70}}
So	now	you	have	to	do	something	with	your	data.	You	will	provide	the	following	menu	to	allow	the	
user	to	perform	actions	on	the	data:
*** Grade Processing Menu ***
-----------------------------
1. List Names
2. Display Student Record by Id
3. Display Student Record by Lastname
4. Display Component
5. Display Total
6. Exit
Enter an option?
A	sample	code	template	implementing	the	above	menu	structure	is	provided	with	this	assignment.
The	options	will	work	as	follows
1. The	List	names	option	simply	displays	the	list	of	students	followed	by	a	line	indicating	how	
many	students	are	there	in	the	file. Example:
(40543437 John Doe)
(40543476 Jane Doe)
(40545276 Michael J)
(40544545 Yo McLovin)
4 students in total.
2. Display	Student	record	by	Id,	lets	the	use	enter	the	student	id	and	then	displays	the	student	
record	as	in	the	following.	You	may	format	the	student	record	as	you	wish	or	simply	dump	
the	record	as	illustrated in	the	following	example:
Enter the Student ID: 40543437
Found:
[40543437 John Doe {A1 {:weight 10, :grade 95.5} A2 {:weight 10, :grade
100} MIDTERM {:weight 20, :grade 80} FINAL {:weight 60, :grade 70}}]
If	the	student	id	is	non-existent,	an	error	message	will	be	displayed.	Example:
Enter the Student ID: 9999
Invalid student ID or student ID not found.
3. Display	Student	record	by	last	name,	searches	for	the	last	name	and	displays the	student	
records	for	matched	students.	Example:
Enter the Last Name: Doe
Found:
[40543437 John Doe {A1 {:weight 10, :grade 95.5} A2 {:weight 10, :grade
100} MIDTERM {:weight 20, :grade 80} FINAL {:weight 60, :grade 70}}]
[440543476 Jane Doe {A1 {:weight 10, :grade 100} A2 {:weight 10, :grade
100} MIDTERM {:weight 20, :grade 90} FINAL {:weight 60, :grade 100}}]
Total 2 record(s) found.
4. Using	display	component	method,	the	user	enters	the	component	name	and	the	system	will	
display	the	result	and	the	class	average (rounded	to	1	digit) for	that	specific	component.	
Example:
Enter Component Name: A1
(40543437 95.5)
(40543476 100)
(40545276 76)
(40544545 60)
Average: 91.1
5. The	display	total	menu	options	displays the	individual	grades	and	the	course	total	for	each	
student	followed	by	the	class	average,	as	illustrated	in	the	following example.	The	‘schema’	
of	the	data	is	displayed	on	top,	listed	all	course	component	in	alphabetical	order. The	
student	list	is	also	sorted	by	student	id	in	ascending	order,	Example:
(STUDID A1 A2 FINAL MIDTERM TOTAL)
(40543437 95.5 100 80 70 77.6)
(40543476 100 100 90 100 98)
(40544545 70 70 60 60 62)
(40545276 99 98 97 76 84.7)
(AVG 91.1 92 81.6 76.5 80.6)
6. Finally,	if	the	Exit	option	is	entered	the	program	will	terminate	with	a	“Good	Bye”	message.	
Otherwise,	the	menu	will	be	displayed	again.	
So	that’s	the	basic	idea.	Here	are	some	additional	points	to	keep	in	mind:
1. You	do	not	want	to	load	the	data	each	time	a	request	is	made.	So,	before	the	menu	is	
displayed	the	first	time,	your	data	should	be	loaded	and	stored	in	appropriate	data	
structures.	So,	assuming	a	loadData function	in	a	module	called	db,	an	invocation	like	the	
following	would	create	and	load	a	data	structure	called	gradesDB
(def gradesDB (db/loadData "grades.txt"))
The	gradesDB data	structure	can	then	be	passed	as	input	to	any	function	that	needs	to	act	
on	the	data.	Note	that,	once	it	is	loaded,	the	data	is	never	updated.
You	may	alternatively	use	clojure’s	read-csv function.
2. As	a	functional	language,	Clojure	uses	recursion.	If	possible,	use	the	map,	reduce and	
filter functions	whenever	you	can.	(Note	that	you	may	sometimes	have	to	write	your	
own	recursive	functions	when	something	unique	is	required).
3. This	is	a	Clojure	assignment,	not	a	Java	assignment.	So	Java	should	not	be	used	for	any	main	
functionality.	That	said,	it	might	be	necessary	to	use	Java	classes	to	convert	text	to	numbers	
in	order	to	do	the	calculations.	An	example	with	the	map function	is	shown	below:
(map #(Integer/parseInt %) ["6" "2" "3"])
4. It	is	worth	noting,	however,	that	this	can	also	be	done	with	Clojure’s	read-string
function.	This	can	be	used	to	translate	“numeric”	strings,	including	floating	point	values.	So	
we	could	do	something	like:
(* 4 (read-string "4.5")) ; = 18
5. The	I/O	in	this	assignment	is	trivial.	While	it	is	possible	to	use	complex	I/O	techniques,	it	is	
not	necessary	to	read	the	text	files.	Instead,	you	should	just	use	slurp,	a	Clojure	function	
that	will	read	a	text	file	into	a	string,	as	in:
(slurp "myFile.txt")
6. For	the	input	from	the	user,	the	read-line function	can	be	used.	If	you	print	a	prompt	
string	to	the	screen	(e.g.,	“please	enter	the	ciry”,	you	may	want	to	use	(flush) before	
(read-line) so	that	the	prompt	is	not	cached	(and	therefore	not	displayed).
7. For	string	processing,	you	will	want	to	use	clojure.string.	You	can	view	the	online	
docs	for	a	list	of	string	functions	and	examples	(https://clojuredocs.org/clojure.string)
8. Do	not	worry	about	efficiency.	There	are	ways	to	make	this	program	(both	the	data	
management	and	the	menu)	more	efficient,	but	that	is	not	our	focus	here.	We	just	want	you	
to	use	basic	functionality	to	try	to	get	everything	working.
DELIVERABLES:	Your	submission	will	have	just	3	source	files.	The	“main”	file	will	be	called	app.clj
and	will	be	used	to	simply	load	the	information	in	the	text	files	into	a	group	of	data	structures	and	
then	pass	this	data	to	the	function	that	will	provide	the	initial	functionality	for	the	app.	The	second	
file,	menu.clj will,	as	the	name	implies,	provide	the	interface	to	the	user.	The	third	file	will	be	
called	db.clj and	will	contain	all	of	the	data	management	code	(loading,	organizing,	etc.).	Do	not	
include	any	data	files	with	your	submission,	as	the	markers	will	provide	their	own.	
PROJECT	ENVIRONMENT:	It	is	easy	to	run	a	single	Clojure	file	from	the	command	line	(i.e.,	clojure
myfile.clj).	It	becomes	a	little	more	tedious	when	the	app	is	made	up	of	multiple	files.	In	
practice,	large	projects	are	typically	built	with	a	tool	called	Leinengen.	Use	of	Leiningen,	however,	is	
overkill for	this	kind	of	assignment	and	will	likely	make	building	and	testing	more	problematic	for	
most	students.
We	will	therefore	keep	things	as	simple	as	possible.	Your	files,	both	source	code	and	data	will	be	
located	in	the	current	folder.	Each	of	the	three	source	files	listed	above	will	define	its	own	
namespace.	For	example:
(ns app
 (:require [db])
 (:require [menu]))

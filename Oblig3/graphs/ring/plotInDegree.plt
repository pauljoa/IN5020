#set terminal png enhanced
set terminal png enhanced
set output 'plot.png'

set title "In-degree distribution"
set xlabel "in-degree"
set ylabel "number of nodes"
set key right top
plot "ids30.txt" title 'Basic Shuffle c = 30' with histeps, \
	"idr30.txt" title 'Random Graph c = 30' with histeps, \
	"ids50.txt" title 'Basic Shuffle c = 50' with histeps, \
	"idr50.txt" title 'Random Graph c = 50' with histeps
	
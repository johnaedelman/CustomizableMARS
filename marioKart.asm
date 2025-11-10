# start at 8th place
put $t0, $t1, 8

# boost up to 1st place
acc $t0
acc $t0
acc $t0

# stop, go back to 12th place
brk $t0

# roll and use mystery box items
roll
roll
use
use
WORKDIR = "<put dir>"
dat <- read.csv("myselections.csv")
# Adjust order
dat <- dat[,-4]
dat <- dat[,c(2,1,3)]
# Get needed library
install.packages("reshape")
library(reshape)
dat2 <- cast(dat, member_full ~ vote_number)
# Change column to row names
data <- dat2[,-1]
rownames(data) <- dat[,1]

# Cluster
data[fit$cluster == 1,]
data[fit$cluster == 2,]
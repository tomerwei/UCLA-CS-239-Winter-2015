import sys
import commands

methods = dict()

methodCounts = dict()

methodCountsForSize = dict()

lastClass = ""

print "beforelineAdded"

linesAdded = commands.getoutput("./number_of_lines_added.sh")

print "AfterlineAdded"

output = linesAdded.split("\n")

for line in output:
	if "Size Difference" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "in method" in line:
		lineAddition = line.split(" ")
		methodName = lineAddition[-1]
		numberOfLines = int(lineAddition[0])
		
		key = lastClass + "." + methodName
		
		if key in methods:
			methods[key]['linesAdded'] = methods[key]['linesAdded'] + numberOfLines
			methodCounts[key] = methodCounts[key] + 1
		else:
			methods[key] = dict()
			methods[key]['linesAdded'] = numberOfLines
			methods[key]['linesDeleted'] = 0
			methods[key]['numberOfLines'] = 0
			methodCounts[key] = 1

#---------------

print "BeforelinesDeleted"

linesDeleted = commands.getoutput("./number_of_lines_deleted.sh")

print "AfterlinesDeleted"

output = linesDeleted.split("\n")

for line in output:
	if "Size Difference" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "in method" in line:
		lineDeletion = line.split(" ")
		methodName = lineDeletion[-1]
		numberOfLines = int(lineDeletion[0])
		
		key = lastClass + "." + methodName
		if key in methods:
			methods[key]['linesDeleted'] = methods[key]['linesDeleted'] + numberOfLines

#---------------

print "BeforetotalNumberOfLines"

totalNumberOfLines = commands.getoutput("./lines_of_codes.sh")

print "AftertotalNumberOfLines"

output = totalNumberOfLines.split("\n")

for line in output:
	if "Size information" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "Method:" in line:
		numLines = line.split(" ")
		methodName = numLines[-2]
		numberOfLines = int(numLines[-1][1:-1])
		
		key = lastClass + "." + methodName
		
		if key in methods:
			methods[key]['numberOfLines'] = methods[key]['numberOfLines'] + numberOfLines
			
			if key in methodCountsForSize:
				methodCountsForSize[key] = methodCountsForSize[key] + 1
			else:
				methodCountsForSize[key] = 1
		else:
			methods[key] = dict()
			methods[key]['linesAdded'] = 0
			methods[key]['linesDeleted'] = 0
			methods[key]['numberOfLines'] = numberOfLines
			methodCountsForSize[key] = 1

#---------------

for key in methodCounts:
	methods[key]['linesAdded'] = methods[key]['linesAdded'] / methodCounts[key]
	methods[key]['linesDeleted'] = methods[key]['linesDeleted'] / methodCounts[key]
	
for key in methodCountsForSize:	
	methods[key]['numberOfLines'] = methods[key]['numberOfLines'] / methodCountsForSize[key]
	
for key in methods:
	print key + " " + str(methods[key]['linesAdded']) + " " + str(methods[key]['linesDeleted']) + " " + str(methods[key]['numberOfLines'])
	

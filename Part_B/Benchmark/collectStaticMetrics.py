import sys
import commands

suspiciousness = open("./reports/closure-compiler/method-scores.csv", 'r')

methods = dict()

methodCounts = dict()

methodCountsForSize = dict()

lastClass = ""

linesAdded = commands.getoutput("./number_of_lines_added.sh")

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

linesDeleted = commands.getoutput("./number_of_lines_deleted.sh")

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
		
		methods[key]['linesDeleted'] = methods[key]['linesDeleted'] + numberOfLines

#---------------

totalNumberOfLines = commands.getoutput("./lines_of_codes.sh")

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
	
#---------------

finalcsv = open("suspiciousness_scores_with_metrics.csv", 'w')

finalcsv.write("Method_Name,Tarantula,Tarantula_Weighted,Sbi,Sbi_Weighted,Jaccard,Jaccard_Weighted,Ochiai,Ochiai_Weighted,Total_Number_Of_Lines,Number_Of_Lines_Added,Number_Of_Lines_Deleted\n")

for line in suspiciousness:

	methodKey = line[0:line.find("(")]
	
	methodKey = methodKey.split("/")[-1]
	
	methodKey = methodKey.replace("<init>", "Constructor", 1)
	
	if methodKey in methods:
		finalcsv.write(line[:-1] + "," + str(methods[methodKey]['numberOfLines']) + "," + str(methods[methodKey]['linesAdded']) + "," + str(methods[methodKey]['linesDeleted']) + "\n")
		
	else:
		finalcsv.write(line[:-1] + ",NA,NA,NA\n")

suspiciousness.close()
finalcsv.close()
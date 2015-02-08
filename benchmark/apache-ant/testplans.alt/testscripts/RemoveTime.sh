cp ${experiment_root}/apache-ant/outputs/t$1 ${experiment_root}/apache-ant/outputs/tmp
sed '/Time:/d' ${experiment_root}/apache-ant/outputs/tmp > ${experiment_root}/apache-ant/outputs/t$1
cp ${experiment_root}/apache-ant/outputs/t$1 ${experiment_root}/apache-ant/outputs/tmp
sed '/OK /d' ${experiment_root}/apache-ant/outputs/tmp > ${experiment_root}/apache-ant/outputs/t$1
cp ${experiment_root}/apache-ant/outputs/t$1 ${experiment_root}/apache-ant/outputs/tmp
sed '/Finished after/d' ${experiment_root}/apache-ant/outputs/tmp > ${experiment_root}/apache-ant/outputs/t$1
rm ${experiment_root}/apache-ant/outputs/tmp

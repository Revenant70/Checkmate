import Task from "./Task";
import ManageTasks from "./ManageTasks";

export default function TableBody() {
  return (
    <>
      <div className="h-screen">
        <Task></Task>
        <ManageTasks></ManageTasks>
      </div>
    </>
  );
}

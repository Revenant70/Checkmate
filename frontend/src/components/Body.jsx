import Task from "./Task";
import AddTask from "./AddTasks";

export default function TableBody() {
  return (
    <>
      <div className="h-screen">
        <Task></Task>
        <AddTask></AddTask>
      </div>
    </>
  );
}

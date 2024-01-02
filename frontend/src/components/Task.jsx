import AddTask from "./TaskBar";
import { useState, useEffect } from "react";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faEllipsis,
  faCheck,
  faTrash,
  faPen,
  faX,
  faArrowLeft,
} from "@fortawesome/free-solid-svg-icons";
import { motion, AnimatePresence } from "framer-motion";
import { useNavigate } from "react-router-dom";

export default function Task() {
  const navigate = new useNavigate();
  const dateFormatRegex = /^(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[01])\s*(1[0-2]|0?[1-9]):[0-5][0-9](AM|PM)$/i;

  const [taskId, setTaskId] = useState("")
  const [taskName, setTaskName] = useState("");
  const [desc, setDescName] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [status, setStatus] = useState("");

  const [tasks, setTasks] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [dueDateError, setDueDateError] = useState("");


  const shakeVariants = {
    initial: { x: 0 },
    shake: {
      x: [-2, 2, -2, 2, 0],
      transition: { duration: 0.275 },
    },
  };

  const completeTask = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("JWT");
      const response = await axios.put(
        `http://localhost:8080/api/tasks/${taskId}/complete`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.status === 200) {
        fetchUserTasks();
        console.log("Task marked as complete");
      }
    } catch (e) {
      console.log("Failed to mark task as complete:", e.message);
    }
  };

  const deleteTask = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("JWT");
      const response = await axios.delete(
        `http://localhost:8080/api/tasks/${taskId}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.status == 200) {
        fetchUserTasks();
        console.log("Task deleted");
      }
    } catch (e) {
      console.log("Task failed to be added to the system:", e.message);
    }
  };

  const editTask = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("JWT");
      const response = await axios.put(
        `http://localhost:8080/api/tasks/${taskId}`,
        {
          title: taskName,
          desc: desc,
          dueDate: dueDate,
          status: status,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
      if (response.status == 200) {
        console.log(response.data);
        fetchUserTasks();
        toggleModalFunction();
        console.log("Task edited");
      }
    } catch (e) {
      console.log("Task failed to be added to the system:", e.message);
    }
  };

  const fetchUserTasks = async () => {
    try {
      const token = localStorage.getItem("JWT");
      const result = await axios.get("http://localhost:8080/api/tasks", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (result.data.isEmpty) {
        console.log("No data");
      } else {
        setTasks(result.data);
      }
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    fetchUserTasks();
  }, []);

  function toggleModalFunction() {
    setIsOpen(!isOpen);
  }

  const handleDueDateChange = (e) => {
    const value = e.target.value;
    setDueDate(value);

    if (value && !dateFormatRegex.test(value)) {
      setDueDateError("Invalid date format");
    } else {
      setDueDateError("");
    }
  };

  function sendBackToAuth() {
    navigate("/auth");
  }

  return (
    <div className="h-screen">
      <div className="fixed bottom-10 left-10 md:top-10 md:bottom-full">
        <FontAwesomeIcon
          className="btn btn-sm"
          icon={faArrowLeft}
          onClick={sendBackToAuth}
        />
      </div>
      <div className="h-5/6 flex justify-center items-start">
        <table className="table w-1/2 overflow-x-auto cols-xs-1 text-center">
          {/* head */}
          <thead>
            <tr>
              <th>Task</th>
              <th>Description</th>
              <th>Due date</th>
              <th>Task Status</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {tasks.length > 0
              ? tasks.map((task, index) => (
                  <tr key={index}>
                    <th>
                      <div className="flex items-center justify-center gap-3">
                        <div className="text-lg">{task.title}</div>
                      </div>
                    </th>
                    <th>
                      <span className="text-xs">{task.desc}</span>
                    </th>
                    <th>
                      <span className="text-sm">{task.dueDate}</span>
                    </th>
                    <th>
                      <span className="text-sm">{task.status}</span>
                    </th>
                    <th>
                      <div className="dropdown dropdown-right">
                        <div tabIndex={0} role="button" className="btn m-1">
                          <FontAwesomeIcon icon={faEllipsis} />
                        </div>
                        <motion.ul
                          tabIndex={0}
                          className="dropdown-content z-[1] menu flex flex-row justify-center items-center p-2 bg-base-100 drop-shadow-lg rounded-box w-52"
                        >
                          <li>
                            <motion.a
                              whileHover="shake"
                              initial="initial"
                              variants={shakeVariants}
                              onClick={(e) => {
                                setTaskId(task.taskid);
                                completeTask(e);
                              }}
                            >
                              <FontAwesomeIcon
                                color="green"
                                size="lg"
                                icon={faCheck}
                              />
                            </motion.a>
                          </li>
                          <li>
                            <motion.a
                              whileHover="shake"
                              initial="initial"
                              variants={shakeVariants}
                              onClick={() => {
                                setIsOpen(!isOpen);
                                setTaskId(task.taskid);
                              }}
                            >
                              <FontAwesomeIcon size="lg" icon={faPen} />
                            </motion.a>
                          </li>
                          <li>
                            <motion.a
                              whileHover="shake"
                              initial="initial"
                              variants={shakeVariants}
                              onClick={(e) => {
                                setTaskId(task.taskid)
                                deleteTask(e);
                              }}
                            >
                              <FontAwesomeIcon
                                color="red"
                                size="lg"
                                icon={faTrash}
                              />
                            </motion.a>
                          </li>
                        </motion.ul>
                      </div>
                    </th>
                  </tr>
                ))
              : null}
          </tbody>
          {/* foot */}
        </table>
        <AnimatePresence>
          {isOpen && (
            <motion.form
              onSubmit={editTask}
              className="card-body w-1/4 h-3/5 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-base-200 drop-shadow-lg rounded-lg flex justify-center align-middle"
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
            >
              <div className="p-4">
                <motion.div
                  className="fixed top-5 right-5"
                  whileHover={{ scale: 1.085 }}
                  whileTap={{ scale: 0.9 }}
                >
                  <FontAwesomeIcon
                    className="cursor-pointer"
                    size="xl"
                    icon={faX}
                    onClick={toggleModalFunction}
                  />{" "}
                </motion.div>
                <div className="form-control">
                  <h2 className="text-2xl font-bold text-center pb-3">
                    Enter task details below
                  </h2>
                  <label className="label">
                    <span className="label-text">Task Name</span>
                  </label>
                  <input
                    type="text"
                    placeholder="task name"
                    onChange={(e) => setTaskName(e.target.value)}
                    className="input input-bordered"
                  />
                </div>
                <div className="form-control">
                  <label className="label">
                    <span className="label-text">Description</span>
                  </label>
                  <input
                    type="text"
                    placeholder="description"
                    onChange={(e) => setDescName(e.target.value)}
                    className="input input-bordered"
                  />
                </div>
                <div className="form-control">
                  <label className="label">
                    <span className="label-text">Due Date</span>
                  </label>
                  <input
                    type="text"
                    placeholder="12/25 5:10PM"
                    value={dueDate}
                    onChange={handleDueDateChange}
                    className={`input input-bordered ${
                      dueDateError ? 'input-error' : ''
                    }`}
                  />
                  {dueDateError && (
                    <p className="error-text">{dueDateError}</p>
                  )}
                </div>
                <div className="form-control">
                  <label className="label">
                    <span className="label-text">Status</span>
                  </label>
                  <select
                    onChange={(e) => setStatus(e.target.value)}
                    className="select select-bordered"
                  >
                    <option disabled selected>
                      Pick a status for this task
                    </option>
                    <option>To Do</option>
                    <option>In Progress</option>
                    <option>Complete</option>
                  </select>
                </div>
                <div className="form-control mt-6">
                  <button className="btn btn-primary">Submit task</button>
                </div>
              </div>
            </motion.form>
          )}
        </AnimatePresence>
      </div>
      <AddTask fetchTasks={fetchUserTasks} />
    </div>
  );
}
